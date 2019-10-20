<template>
  <section>
    <div class="tile is-ancestor">

      <div class="tile is-3"><!-- Spacer tile --></div>

      <div class="tile is-6" style="display:inline-block; text-align: center; overflow: auto;">
        <h2  class=is-subpage-title>Import data from csv</h2>
        <label><b>upload a csv file</b></label>
        <b-field class="file custom-centered">
          <b-upload v-model="file" class="custom-centered" v-on:input="unwrapFile">
            <a class="button is-primary">
              <span>upload csv</span>
            </a>
          </b-upload>
        </b-field>

        <div class="separator-line"></div>

        <b-table :data="file ? [file] : [{}]" class="is-table custom-centered">
          <template slot-scope="props">
            <b-table-column field="name" label="Name">
              {{ props.row.name }}
            </b-table-column>

            <b-table-column label="Size" numeric>
              {{ formatFileSize }}
            </b-table-column>

            <b-table-column label="Amount of Objects" numeric>
              {{ fileLines }}
            </b-table-column>
          </template>
        </b-table>

        <p>current status: <b>{{status}}</b></p>

        <b-button class="button is-primary is-submit-button" @click="postData" v-if="postPossible">upload data</b-button>
      </div>
    </div>
  </section>
</template>

<script>
  import stringUtils from "../utils/stringUtils";
  import api from "../api/api";

  export default {
    name: "ImportPage",

    data() {
      return {
        file: null,
        fileLines: null,
        headers: null,
        fileData: null,

        status: 'waiting for file',
        postPossible: false,
        units: ['Bytes', 'KB', 'MB', 'GB', 'TB']
      }
    },

    computed: {
      formatFileSize() {
        if(!this.file) return;
        let fileSize = this.file.size;
        let unitInd = 0;
        while(fileSize > 1024) {
          fileSize /= 1024;
          unitInd++;
        }
        return fileSize.toFixed(2) + ' ' + this.units[unitInd];
      },
    },

    methods: {
      unwrapFile() {
        this.setStatus('loading file');
        this.file.text().then((unwrapped) => {
          let lines = stringUtils.stringToLines(unwrapped);
          this.fileLines = lines.length - 1;
          this.headers = lines[0].split(',').map(elem => stringUtils.unquote(elem));
          this.fileData = lines.slice(1).map(elem => elem.split(',').map(el => stringUtils.unquote(el)));
          this.verifyValidity();
        });
      },

      verifyValidity() {
        this.setStatus('verifying format of file');
        let artistNameInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'artist.name'));
        let artistTermsInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'artist.terms'));
        let songTitleInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'song.title'));
        let songDurationInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'song.duration'));
        let songYearInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'song.year'));

        if(artistNameInd > -1 && artistTermsInd > -1 && songTitleInd > -1 && songDurationInd > -1 && songYearInd > -1) {
          console.log('valid file');
          this.postPossible = true;
          this.setStatus('ready to upload data');
        }
        else {
          this.$buefy.toast.open({
            message: 'not all required fields could be found in the file, missing fields are:\n' +
              (artistNameInd < 0 ? 'artist.name ' : '') + (artistTermsInd < 0 ? 'artist.terms ' : '') +
              (songTitleInd < 0 ? 'song.title ' : '') + (songDurationInd < 0 ? 'song.duration ' : '') +
              (songYearInd < 0 ? 'song.year' : ''),
            type: 'is-danger',
            duration: 10000,
          });
          this.setStatus('unable to upload data');
        }
      },

      postData() {
        let artistNameInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'artist.name'));
        let artistTermsInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'artist.terms'));
        let songTitleInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'song.title'));
        let songDurationInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'song.duration'));
        let songYearInd = this.headers.findIndex(elem => stringUtils.stringCompIC(elem, 'song.year'));

        this.setStatus('starting upload');
        for(let i = 0; i < this.fileLines; i++) {
          this.setStatus('uploading object ' + i);
          //first post the artist
          let artistName = this.fileData[i][artistNameInd];
          let artistTerms = this.fileData[i][artistTermsInd];
          let songTitle = this.fileData[i][songTitleInd];
          let songDuration = this.fileData[i][songDurationInd];
          let songYear = this.fileData[i][songYearInd];

          api.postArtist(artistName, artistTerms).then((response) => {
            if(response.status === 200) {
              
            }
          });
        }
      },



      setStatus(status) {
        this.status = status;
      },
    },
  }
</script>

<style scoped>

</style>
