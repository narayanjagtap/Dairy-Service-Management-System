import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';

// Pages
import Login from './pages/Login';
import Register from './pages/Register';
import CustomerDashboard from './pages/CustomerDashboard';
import CreateRequest from './pages/CreateRequest';
import MyRequests from './pages/MyRequests';
import MyBills from './pages/MyBills';
import AdminDashboard from './pages/AdminDashboard';
import AllRequests from './pages/AllRequests';
import CreateBill from './pages/CreateBill';
import BillsManagement from './pages/BillsManagement';
import AllCustomers from './pages/AllCustomers';

// Components
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';

// Services
import { authService } from './services/authService';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userRole, setUserRole] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Check if user is already logged in
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    
    if (token && role) {
      setIsAuthenticated(true);
      setUserRole(role);
    }
    setLoading(false);
  }, []);

  const handleLogin = (role) => {
    setIsAuthenticated(true);
    setUserRole(role);
  };

  const handleLogout = async () => {
    await authService.logout();
    setIsAuthenticated(false);
    setUserRole(null);
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <Router>
      {isAuthenticated && <Navbar onLogout={handleLogout} userRole={userRole} />}
      <main className="app-main">
        <Routes>
          {/* Public Routes */}
          <Route
            path="/login"
            element={
              isAuthenticated ? (
                <Navigate to={userRole === 'ADMIN' ? '/admin/dashboard' : '/customer/dashboard'} />
              ) : (
                <Login onLogin={handleLogin} />
              )
            }
          />
          <Route
            path="/register"
            element={
              isAuthenticated ? (
                <Navigate to={userRole === 'ADMIN' ? '/admin/dashboard' : '/customer/dashboard'} />
              ) : (
                <Register />
              )
            }
          />

          {/* Customer Routes */}
          <Route element={<ProtectedRoute requiredRole="CUSTOMER" />}>
            <Route path="/customer/dashboard" element={<CustomerDashboard />} />
            <Route path="/customer/requests" element={<MyRequests />} />
            <Route path="/customer/bills" element={<MyBills />} />
            <Route path="/customer/create-request" element={<CreateRequest />} />
          </Route>

          {/* Admin Routes */}
          <Route element={<ProtectedRoute requiredRole="ADMIN" />}>
            <Route path="/admin/dashboard" element={<AdminDashboard />} />
            <Route path="/admin/requests" element={<AllRequests />} />
            <Route path="/admin/create-bill" element={<CreateBill />} />
            <Route path="/admin/bills" element={<BillsManagement />} />
            <Route path="/admin/customers" element={<AllCustomers />} />
          </Route>

          {/* Default Route */}
          <Route
            path="/"
            element={
              isAuthenticated ? (
                <Navigate to={userRole === 'ADMIN' ? '/admin/dashboard' : '/customer/dashboard'} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />

          {/* 404 Not Found */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </main>
    </Router>
  );
}

export default App;
