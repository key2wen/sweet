
export default class EventEmitter {
  static _events = {}

  /**
   * 订阅一个事件
   */
  static subscribe(event, callback) {
    if (!EventEmitter._events[event]) {
      EventEmitter._events[event] = [];
    }
    EventEmitter._events[event].push(callback);
  }

  /**
   * 通知监听者
   */
  static dispatch(event, data) {
    if (!EventEmitter._events[event]) {
      return;
    }
    for (let i = 0; i < EventEmitter._events[event].length; i++) {
      EventEmitter._events[event][i](data);
    }
  }
}
