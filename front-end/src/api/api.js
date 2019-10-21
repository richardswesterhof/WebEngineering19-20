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
      return error;
    });
  },


  getArtist(artistid) {
    if(!artistid) return new Promise(function(resolve) {
      resolve({error: 'no artistid given'});
    });
    return axios.get('/api/artists/' + artistid).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  getArtistStats(artistid, year) {
    if(!artistid) return new Promise(function(resolve) {
      resolve({error: 'no artistid given'});
    });
    return axios.get('/api/artists/' + artistid + '/statistics?year=' + (year ? year : '')).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  getArtistsByPopularity(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to artists/popular');
    return axios.get('/api/artists/popular' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  getSongs(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to songs with: ' + queryParameters);
    return axios.get('/api/songs' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  getSong(songid) {
    if(!songid) return new Promise(function(resolve) {
      resolve({error: 'no songid given'});
    });
    return axios.get('/api/songs/' + songid).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },

  getSongsByPopularity(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to songs/popularity with ' + queryParameters);
    return axios.get('/api/songs/popularity' + queryParameters).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  postArtist(name, terms) {
    let requestBody = {name: name, terms: terms};
    return axios.post('/api/artists', requestBody).then((response) => {
      return response;
    });
  },


  postSong(artistid, title, duration, year) {
    let requestBody = {artistid: artistid, title: title, duration: duration, year: year};
    return axios.post('/api/songs', requestBody).then((response) => {
      return response;
    });
  },


  putArtist(artistid, name, terms) {
    let requestBody = {name: name, terms: terms};
    return axios.put('/api/artists/' + artistid, requestBody).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  putSong(songid, artistid, title, duration, year) {
    let requestBody = {artistid: artistid, title: title, duration: duration, year: year};
    return axios.put('/api/songs/' + songid, requestBody).then((response) => {
      return response;
    }, (error) => {
      return error;
    });
  },


  deleteArtist(artistid) {
    return axios.delete('/api/artists/' + artistid).then((response) => {
      return response;
    }, (error) => {
      return error;
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
