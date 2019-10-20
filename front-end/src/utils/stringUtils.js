export default {

  stringToLines(input) {
    return input.split(/[\r\n]+/);
  },

  /**
   * searches if a string contains a given string, ignoring their case
   * @param base: the string to search in
   * @param search: the string to be searched
   * @return boolean: whether the string was found or not
   */
  stringCompIgnoreCase(base, search) {
    return base.toLowerCase() === search.toLowerCase();
  },

  /**
   * provides an alias for stringCompIgnoreCase
   * @param base
   * @param search
   * @returns {*|boolean}
   */
  stringCompIC(base, search) {
    return this.stringCompIgnoreCase(base, search);
  },

  unquote(input) {
    return input.split('').filter(elem => elem !== '\"').filter(elem => elem.match(/\S/)).join('');
  },
}
