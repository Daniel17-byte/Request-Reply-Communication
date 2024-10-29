import UserManagement from "./user/UserManagement";
import DeviceManager from "./device/DeviceManager";

function App() {
  return (
    <>
        <div className="App">
            <UserManagement/>
            <DeviceManager/>
        </div>
    </>
  );
}

export default App;
