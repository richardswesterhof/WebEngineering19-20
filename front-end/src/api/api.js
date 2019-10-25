import axios from 'axios';
import unnullifier from '../utils/unnullifier.js';


/**
 * this class provides a wrapper around the actual back end for ease of use in the front end
 */
export default {

  getArtists(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to artists with: ' + queryParameters);
    return axios.get('/api/artists' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  getArtist(artistid) {
    if(!artistid) return new Promise(function(resolve) {
      resolve({error: 'no artistid given'});
    });
    return axios.get('/api/artists/' + artistid).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  getArtistStats(artistid, year) {
    if(!artistid) return new Promise(function(resolve) {
      resolve({error: 'no artistid given'});
    });
    return axios.get('/api/artists/' + artistid + '/statistics?year=' + (year ? year : '')).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  getArtistsByPopularity(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to artists/popularity with ' + queryParameters);
    return axios.get('/api/artists/popularity' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  getSongs(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to songs with: ' + queryParameters);
    return axios.get('/api/songs' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  getSong(songid) {
    if(!songid) return new Promise(function(resolve) {
      resolve({error: 'no songid given'});
    });
    return axios.get('/api/songs/' + songid).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },

  getSongsByPopularity(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to songs/popularity with ' + queryParameters);
    return axios.get('/api/songs/popularity' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  postArtist(name, terms, hotness) {
    let requestBody = {name: name, terms: terms, hotness: hotness};
    return axios.post('/api/artists', requestBody).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  postSong(artistid, title, duration, year, hotness, datasetid) {
    let requestBody = {artistid: artistid, title: title, duration: duration, year: year, songid: datasetid, hotness: hotness};
    return axios.post('/api/songs', requestBody).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  putArtist(artistid, name, terms, hotness) {
    let requestBody = {name: name, terms: terms, hotness: hotness};
    return axios.put('/api/artists/' + artistid, requestBody).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  putSong(songid, artistid, title, duration, year, hotness, datasetid) {
    let requestBody = {artistid: artistid, title: title, duration: duration, year: year, hotness: hotness, songid: datasetid};
    return axios.put('/api/songs/' + songid, requestBody).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  deleteArtist(artistid) {
    return axios.delete('/api/artists/' + artistid).then((response) => {
      return response;
    }, (error) => {
      return error.response;
    });
  },


  convertFilterArrayToParametersString(filters) {
    let queryParameters = '?';
    if(filters.constructor === Array) {
      //extracts all fields from the objects in the filters array
      for(let i = 0; i < filters.length; i++) {
        if(i !== 0) queryParameters += '&';
        let param = filters[i].filterName;
        queryParameters += param + '=' + unnullifier.toNNS(filters[i].filterValue);
      }
    }

    return queryParameters;
  },
}
