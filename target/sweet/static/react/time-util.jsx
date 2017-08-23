function timeFormat(ms) {
  let t = '';
  if (ms > -1) {
    ms = parseInt(ms / 1000);
    let hour = Math.floor(ms / 3600);
    let min = Math.floor(ms / 60) % 60;
    let sec = ms % 60;
    let day = parseInt(hour / 24);
    if (day > 0) {
      hour = hour - 24 * day;
      t += day + "天 ";
    }
    if (hour > 0) {
      t += hour + "小时";
    }
    if (min > 0) {
      t += min + "分";
    }
    t += sec + "秒";
  }
  return t;
}

export { timeFormat };
