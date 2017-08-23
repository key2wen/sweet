import React from 'react';
import { timeFormat } from './time-util.jsx';
import 'antd/style/index.less';
import { message } from 'antd';

export default class Timer extends React.Component {

  static propTypes = {
    limit: React.PropTypes.number.isRequired,
    remind: React.PropTypes.func.isRequired,
  }

  state = {
    remaining: this.props.limit,
  }

  constructor(props) {
    super(props);
    const start = Date.now();
    this.interval = setInterval(() => {
      let curr = Date.now();
      let remaining = this.props.limit + start - curr;
      if (remaining <= 0) {
        message.warn("时间到!");
        clearInterval(this.interval);
        this.props.remind();
      } else {
        this.setState({ remaining });
      }
    }, 100);
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  render() {
    return (
      <span>{ timeFormat(this.state.remaining) }</span>
    );
  }
}
