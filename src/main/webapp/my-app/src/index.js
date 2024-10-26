import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './pages/App';
import {Provider} from "react-redux";
import store from "./store/Store"

const root = ReactDOM.createRoot(document.getElementById('root'));
function render () {
    root.render(
        <React.StrictMode>
            <Provider store={store}>
                <App />
            </Provider>
        </React.StrictMode>
    );

}


store.subscribe(render);
render();
