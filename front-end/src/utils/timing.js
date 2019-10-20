export default {

  sleep(milliseconds) {
    return new Promise(function(resolve) {setTimeout(resolve, milliseconds)});
  }
}
