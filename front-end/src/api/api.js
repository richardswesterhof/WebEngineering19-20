import axios from 'axios';
import unnullifier from '../utils/unnullifier.js';

export default {

  getTestObject() {
    return new Promise(function(resolve) {
      resolve({test: 123});
    });
  },

  getArtists(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query with: ' + queryParameters);
    return axios.get('/api/artists' + queryParameters).then((response) => {
      return response.data;
    });
  },

  getSongs(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query with:' + queryParameters);
    return axios.get('/api/songs' + queryParameters).then((response) => {
      return response.data;
    });
  },

  getSong(songid) {
    if(!songid) return new Promise(function(resolve) {
      resolve({error: 'no songid given'});
    });
    return axios.get('/api/songs/' + songid).then((response) => {
      return response.data;
    });
  },

  convertFilterArrayToParametersString(filters) {
    let queryParameters = '?';
    if(filters.constructor === Array) {
      //extracts all fields from the objects in the filters array
      for(let i = 0; i < filters.length; i++) {
        if(i !== 0) queryParameters += '&';
        let param = Object.keys(filters[i])[0];
        queryParameters += param + '=' + unnullifier.toNNS(filters[i][param]);
      }
    }

    return queryParameters;
  },


}
