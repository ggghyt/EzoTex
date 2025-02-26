const dateFormat = function(value, format) {
  let date = value == null ? new Date() : new Date(value);

  let year = date.getFullYear();
  let month = ('0' + (date.getMonth() + 1)).slice(-2);
  let day = ('0' + date.getDate()).slice(-2);

  let result = format.replace('yyyy', year)
      .replace('MM', month)
      .replace('dd', day);
  return result;
}

const timeFormat = function(value, format) {
  let date = value == null ? new Date() : new Date(value);

  let hour = ('0' + date.getHours()).slice(-2);
  let minute = ('0' + date.getMinutes()).slice(-2);
  let second = ('0' + date.getSeconds()).slice(-2);

  let result = format.replace('hh', hour)
                     .replace('mm', minute)
                     .replace('ss', second);
  return result;
}
export default {
  dateFormat,
  timeFormat
}