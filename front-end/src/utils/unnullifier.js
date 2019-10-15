export default {

  toNotNullString(object) {
    if(!object) return '';
    let num = Number(object);
    return isNaN(num) ? object : num;
  },

  /**
   * provides a shorter alias from toNotNullString
   */
  toNNS(object) {
    return this.toNotNullString(object);
  },
}
