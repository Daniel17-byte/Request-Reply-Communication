import UserManagement from "./user/UserManagement";
import DeviceManager from "./device/DeviceManager";
import {BrowserRouter, useRoutes} from "react-router-dom";
import Home from "./components/Home";
import Admin from "./components/Admin";
import Client from "./components/Client";

const AppRoutes = () => {
    return useRoutes([
        {path: '/', element: <Home/>},
        {path: '/admin', element: <Admin/>},
        {path: '/client', element: <Client/>},
        {path: '/users', element: <UserManagement/>},
        {path: '/devices', element: <DeviceManager/>},
    ]);
};

function App() {
  return (
    <>
        <div className="App">
            <BrowserRouter>
                <AppRoutes />
            </BrowserRouter>
        </div>
    </>
  );
}

export default App;
