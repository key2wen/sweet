import 'antd/lib/index.css';
import '../../css/article/view.css';
import React from 'react';
import ReactDOM from 'react-dom';
import EventEmitter from '../emitter.jsx';

let canClick = true;
class Option extends React.Component {

  static propTypes = {
    option: React.PropTypes.string.isRequired,
    desc: React.PropTypes.string.isRequired,
    selected: React.PropTypes.bool.isRequired,
    handleSelect: React.PropTypes.func.isRequired,
  }

  handleClick = () => {
    if (canClick) {
      this.props.handleSelect(this.props.option);
    }
  }

  render() {
    const selectedClass = this.props.selected ? " option_option_selected" : "";
    return (
      <div className={ `option ${ selectedClass }` } onClick={ this.handleClick } >
        <div className={ `option_option ${ selectedClass }` }>{ this.props.option }</div>
        <div className="option_desc">{ this.props.desc != '' ? this.props.desc : <span>&nbsp;</span> }</div>
      </div>
    );
  }
}

EventEmitter.subscribe('slideBeforeChange', () => {
  canClick = false;
});
EventEmitter.subscribe('slideAfterChange', () => {
  canClick = true;
});

export default class Question extends React.Component {

  static propTypes = {
    qid: React.PropTypes.number.isRequired,
    index: React.PropTypes.number.isRequired,
    description: React.PropTypes.string.isRequired,
    optionA: React.PropTypes.string.isRequired,
    optionB: React.PropTypes.string.isRequired,
    optionC: React.PropTypes.string.isRequired,
    optionD: React.PropTypes.string.isRequired,
  }

  state = {
    selectedOption: '',
  }

  constructor(props) {
    super(props);
    this.handleSelect = this.handleSelect.bind(this);
  }

  handleSelect(option) {
    this.setState({ selectedOption: option });
    this.props.handleSelect(this.props.index, option);
  }

  render() {
    const nextTipStyle = {display: this.props.index === 0 ? 'block' : 'none'};
    return (
      <div className="question">
        <div className="question_desc">
          <div className="question_desc_tips">题目:</div>
          <p> {this.props.index + 1}. {this.props.description} </p>
        </div>

        <div className="question_select">
          <div className="question_select_tips">选择答案:</div>
          <Option option="A" desc={this.props.optionA} handleSelect={ this.handleSelect }
                  selected={this.state.selectedOption === 'A'}/>
          <Option option="B" desc={this.props.optionB} handleSelect={ this.handleSelect }
                  selected={this.state.selectedOption === 'B'}/>
          <Option option="C" desc={this.props.optionC} handleSelect={ this.handleSelect }
                  selected={this.state.selectedOption === 'C'}/>
          <Option option="D" desc={this.props.optionD} handleSelect={ this.handleSelect }
                  selected={this.state.selectedOption === 'D'}/>
        </div>

        <div className="question_selected">
          您选择了: <i>{this.state.selectedOption}</i>
        </div>

        <div className="question_next_tip" style={ nextTipStyle }>
          <div>
            向左滑动到下一题
          </div>
        </div>

      </div>
    );
  }
}
