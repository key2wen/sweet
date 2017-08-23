import React from 'react';
import { Select } from 'antd';
import Ajax from './ajax.jsx'

const Option = Select.Option;

export default class AjaxSelect extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            options : []
        }
        this.initOptions();
    }

    initOptions() {
        Ajax.get({
            url: this.props.url
        }).then(jsonWrapper => {
            jsonWrapper.result.list.forEach(ele => {
                let {id, name} = ele;
                this.state.options.push(<Option key={id} value={id.toString()}>{name}</Option>)
            });
        });
    }

    render() {
        return (
            <Select {...this.props}>
                {this.state.options}
            </Select>
        );
    }

}
