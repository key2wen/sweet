import 'antd/lib/index.css';
import '../css/register.css';
import React from 'react';
import { Select } from 'antd';

const Option = Select.Option;

export default class EmailSelect extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      options: []
    }
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(value) {
    let options;
    if (!value || value.indexOf('@') >= 0) {
      options = [];
    } else {
      options = ['qq.com', 'gmail.com', '163.com', '126.com', 'yahoo.com.cn', 'sina.com', 'youngstack.com'].map((domain) => {
        const email = `${value}@${domain}`;
        return <Option key={email}>{email}</Option>;
      });
    }
    //this.setState({options});
    this.state = {options};
  }

  render() {
    // filterOption 需要设置为 false，数据是动态设置的
    return (
      <Select combobox
        {...this.props}
              style={{ width: '100%' }}
              onSearch={this.handleChange}
              filterOption={false}
              placeholder="请输入邮箱地址">
        {this.state.options}
      </Select>
    );
  }
}
