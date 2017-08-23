import $ from 'jquery';

export default class Ajax {
  static ajax(param, type) {
    const ajaxType = param.type || type || 'get';
    return new Promise((resolve) => {
      $.ajax({
        type: ajaxType,
        url: param.url,
        data: param.data,
        async: param.async ? param.async : false,
        success: jsonWrapper => {
          console.log(jsonWrapper);
          resolve(jsonWrapper);
          //if (jsonWrapper && jsonWrapper.status && jsonWrapper.status.status_code === 0) {
          //
          //}
        },
        error: (xhr, status, err) => {
          console.error(param.url, status, err.toString());
        },
      });
    });
  }

  static get(param) {
    return Ajax.ajax(param, 'get');
  }

  static post(param) {
    return Ajax.ajax(param, 'post');
  }
}
