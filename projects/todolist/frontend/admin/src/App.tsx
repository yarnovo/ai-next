import { Routes, Route, Navigate } from 'react-router-dom'
import Layout from './components/Layout'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import TodoList from './pages/TodoList'
import UserList from './pages/UserList'

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<Layout />}>
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="todos" element={<TodoList />} />
        <Route path="users" element={<UserList />} />
      </Route>
    </Routes>
  )
}

export default App
