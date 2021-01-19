import React from 'react';
import ReactDOM from 'react-dom';

const ROOT = document.querySelector('#root');

// 是否沸腾判断组件
function BoilingVerdict (props){
    if (props.celsius >= 100){
        return <p>水会沸腾</p>;
    }
    return <p>水不会沸腾</p>;
}

// 温度的类型
const scaleNames = {
    c: '摄氏度',
    f: '华氏度'
};

// 华氏度转换为摄氏度
function toCelsius(fahrenheit) {
    return (fahrenheit - 32) * 5 / 9;
}

// 摄氏度转换为华氏度
function toFahrenheit(celsius) {
    return (celsius * 9 / 5) + 32;
}

// 当输入 temperature 的值无效时，函数返回空字符串，反之，则返回保留三位小数并四舍五入后的转换结果
function tryConvert(temperature, convert) {
    const input = parseFloat(temperature);
    if (Number.isNaN(input)) {
      return '';
    }
    const output = convert(input);
    const rounded = Math.round(output * 1000) / 1000;
    return rounded.toString();
}


// 温度显示组件
class TemperatureInput extends React.Component {
    handleChange = (e) => {
        this.props.onTemperatureChange(e.target.value);
    }
    render (){
        // 温度
        const temperature = this.props.temperature;
        // 温度类型
        const scale = this.props.scale;
        return (
            <fieldset>
                <legend>输入{scaleNames[scale]}温度:</legend>
                <input value={temperature} onChange={this.handleChange}/>
            </fieldset>
        );
    }
}

class Calculator extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            // 温度
            temperature: '37',
            // 温度类型
            scale: 'c'
        }
    }

    // 摄氏度被修改了
    handleCelsiusChange = (temperature) => {
        this.setState({scale: 'c', temperature});
    }
    // 华氏度被修改了
    handleFahrenheitChange = (temperature) => {
        this.setState({scale: 'f', temperature});
    }
   
    render (){
        // 温度
        const temperature = this.state.temperature;
        // 温度类型
        const scale = this.state.scale;
        // 计算出摄氏度
        const celsius = scale === 'f' ? tryConvert(temperature, toCelsius) : temperature;
        // 计算出华氏度
        const fahrenheit = scale === 'c' ? tryConvert(temperature, toFahrenheit) : temperature;

        return (
            <div>
                <TemperatureInput 
                    scale="c"
                    temperature={celsius}
                    onTemperatureChange={this.handleCelsiusChange}
                    />
                <TemperatureInput scale="f"
                    temperature={fahrenheit}
                    onTemperatureChange={this.handleFahrenheitChange}
                    />
                <BoilingVerdict celsius={parseFloat(celsius)} />
            </div>
        );
    }
}

ReactDOM.render(<Calculator></Calculator>, ROOT);
