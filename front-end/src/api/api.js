import axios from 'axios';
import unnullifier from '../utils/unnullifier.js';


/**
 * this class provides a wrapper around the actual back end for ease of use in the front end
 */
export default {

  getTestObject() {
    return new Promise(function(resolve) {
      resolve({test: 123});
    });
  },


  getArtists(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to artists with: ' + queryParameters);
    return axios.get('/api/artists' + queryParameters).then((response) => {
      return response.data;
    },
      (error) => {
        return [];
    });
  },


  getArtist(artistid) {
    if(!artistid) return new Promise(function(resolve) {
      resolve({error: 'no artistid given'});
    });
    return axios.get('/api/artists/' + artistid).then((response) => {
      return response.data;
    },
      (error) => {
        return {};
      });
  },


  getArtistStats(artistid, year) {
    if(!artistid) return new Promise(function(resolve) {
      resolve({error: 'no artistid given'});
    });
    return axios.get('/api/artists/' + artistid + '/statistics?year=' + (year ? year : '')).then((response) => {
      return response.data;
    },
      (error) => {
        return {};
      });
  },


  getSongs(filters) {
    let queryParameters = this.convertFilterArrayToParametersString(filters);
    console.log('making query to songs with: ' + queryParameters);
    return axios.get('/api/songs' + queryParameters).then((response) => {
      return response.data;
    },
      (error) => {
        return [];
      });
  },


  getSong(songid) {
    if(!songid) return new Promise(function(resolve) {
      resolve({error: 'no songid given'});
    });
    return axios.get('/api/songs/' + songid).then((response) => {
      return response.data;
    },
      (error) => {
        return {};
      });
  },


  postArtist(name, terms) {
    let requestBody = {name: name, terms: terms};
    return axios.post('/api/artists', requestBody).then((response) => {
      return response.data;
    });
  },


  postSong(artistid, title, duration, year) {
    let requestBody = {artistid: artistid, title: title, duration: duration, year: year};
    return axios.post('api/songs', requestBody).then((response) => {
      return response.data;
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
