import React from 'react';
import ReactDOM from 'react-dom';

const element = document.querySelector('#root');
// -------------------
export const themes = {
    light: {
      foreground: '#000000',
      background: '#eeeeee',
    },
    dark: {
      foreground: '#ffffff',
      background: '#222222',
    },
};

const ThemeContext =  React.createContext({  // 俩属性
    theme: themes.light,
    toggleTheme: () => {}
});

function ThemeTogglerButton (){
    return (
        <ThemeContext.Consumer>
            {
                ({theme, toggleTheme}) => { // 俩属性
                    return (
                        <button onClick={toggleTheme} style={{backgroundColor: theme.background}}>Toggle Theme</button>
                    )
                }
            }
        </ThemeContext.Consumer>
    )
}

function Context () {
    return (
        <div>
            <ThemeTogglerButton></ThemeTogglerButton>
        </div>
    )
}

class App extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            theme: themes.dark,
            toggleTheme: this.toggleTheme  // state里面的一个属性指向自己的方法
        }
    }
    toggleTheme = () => {
        console.log('val');
        this.setState((state, props) => {
            return {
                theme: state.theme === themes.dark
                    ? themes.light
                    : themes.dark

            }
        });
    }
    render (){
        return (
            <ThemeContext.Provider value={this.state}>
                <Context></Context>
            </ThemeContext.Provider>
        )
    }
}

ReactDOM.render(<App/>, element);


