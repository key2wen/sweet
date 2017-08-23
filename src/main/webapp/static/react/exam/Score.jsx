import 'antd/style/index.less';
import React from 'react';
import { Alert, Button } from 'antd';
import '../../less/exam.less';

export default class Score extends React.Component {

  handleClick = () => {
    console.log("证书查询")
  }

  render() {
    let checkBtn = null;

    let scoreAlert = null;
    if (global.score) {
      if (global.score.isPass) {
        checkBtn = <Button size="large" type="ghost" onClick={ this.handleClick }> 证 书 查 询 </Button>
        scoreAlert = (<Alert message={ <span> 恭喜你,考试通过了! </span> }
                             description={
                              <div>
                              考试成绩: <font color="red"> { parseInt(global.score.score) } </font> 分 <br/>
                              及格成绩: { parseInt(window.exam.passScore) }  <br/>
                              考试通过, 请回到微信导出证书, 完成学时认证
                              </div>
                             }
                             type="success"
                             showIcon/>);
      } else {
        checkBtn = <Button size="large" type="ghost" disabled> 证 书 查 询 </Button>;
        scoreAlert = (<Alert message={ <span> 考试不通过! </span> }
                             description={
                              <div>
                              考试成绩: <font color="red"> { parseInt(global.score.score) } </font> 分 <br/>
                              及格成绩: { parseInt(global.exam.passScore)}  <br/>
                              </div>
                              }
                             type="error"
                             showIcon/>);
      }
    }
    return (
      <div className="score">
        <br/>
        <h3 style={{ color: 'gray' }}>天天微信课考试成绩</h3>
        <br/>
        { scoreAlert }
        <br/>
        <Alert message="个人信息"
               description={(<div>用户名: { global.user.username } <br/> 姓名: { global.user.name } </div>)}
               type="info"
               showIcon/>
        <br/>
        <div className="entry_button">
          { checkBtn }
        </div>

      </div>
    );
  }
}
