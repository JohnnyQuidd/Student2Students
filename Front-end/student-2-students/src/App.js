import './App.css'
import Welcome from './Components/Welcome/Welcome'
import StudentDashboard from './Components/Dashboard/StudentDashboard'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import NotFound from './Components/Error/NotFound';
import AdminDashboard from './Components/Dashboard/AdminDashboard';
import UniversityManagement from './Components/Dashboard/UniversityManagement';
import AdminCountry from './Components/Dashboard/AdminCountry';
import StudentProfile from './Components/Dashboard/StudentProfile';
import HomePage from './Components/Home/HomePage';
import NewPost from './Components/Post/NewPost';
import PostPreview from './Components/Post/PostPreview';
import Member from './Components/Member/Member';
import NewExchange from './Components/Exchange/NewExchange';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/login" exact component={Welcome} />
          <Route path="/home" component={StudentDashboard} />
          <Route path="/profile" exact component={StudentProfile} />
          <Route path="/post" exact component={NewPost} />
          <Route path="/post/:postHeadline" component={PostPreview} />
          <Route path="/member/:username" component={Member} />
          <Route path="/admin" exact component={AdminDashboard} />
          <Route path="/admin/university" component={UniversityManagement} />
          <Route path="/admin/country" component={AdminCountry} />
          <Route path="/new-exchange" exact component={NewExchange} />
          <Route component={NotFound} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
