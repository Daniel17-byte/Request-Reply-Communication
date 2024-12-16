import UserManagement from "./user/UserManagement";
import DeviceManager from "./device/DeviceManager";
import {BrowserRouter, useRoutes} from "react-router-dom";
import Home from "./components/Home";
import Admin from "./components/Admin";
import Client from "./components/Client";
import Chat from "./components/Chat";

const AppRoutes = () => {
    return useRoutes([
        {path: '/', element: <Home/>},
        {path: '/admin', element: <Admin/>},
        {path: '/client', element: <Client/>},
        {path: '/users', element: <UserManagement/>},
        {path: '/devices', element: <DeviceManager/>},
        {path: '/chat/userA', element: <Chat username="User A"/>},
        {path: '/chat/userB', element: <Chat username="User B"/>},
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
