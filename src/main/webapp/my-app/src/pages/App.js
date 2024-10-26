import React from 'react';
import "../css/main.css"
import EnterPage from "./EnterPage"
import ResultPage from "./ResultPage";
import {useDispatch, useSelector} from "react-redux";
import PullDot from "../api/PullDot";
import {addDot} from "../store/DotsSlice";


function App () {
    // let authorized = useSelector((state) => state.auth.logged).payload;
    //
    // if (authorized) {
    //     return (
    //         <div>
    //             <ResultPage/>
    //         </div>
    //     )
    // } else {
    //     return (
    //         <div>
    //             <EnterPage/>
    //         </div>
    //     )
    // }
    return (
            <div>
                <EnterPage/>
            </div>
        )
}


export default App;
