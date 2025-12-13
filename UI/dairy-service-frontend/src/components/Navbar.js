import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Navbar.css';

function Navbar({ onLogout, userRole }) {
  const navigate = useNavigate();
  const userName = localStorage.getItem('userName');

  const handleLogout = async () => {
    await onLogout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          Dairy Service
        </Link>

        <ul className="navbar-menu">
          {userRole === 'CUSTOMER' && (
            <>
              <li>
                <Link to="/customer/dashboard">Dashboard</Link>
              </li>
              <li>
                <Link to="/customer/requests">My Requests</Link>
              </li>
              <li>
                <Link to="/customer/bills">Bills</Link>
              </li>
              <li>
                <Link to="/customer/create-request" className="btn-create">
                  + New Request
                </Link>
              </li>
            </>
          )}

          {userRole === 'ADMIN' && (
            <>
              <li>
                <Link to="/admin/dashboard">Dashboard</Link>
              </li>
              <li>
                <Link to="/admin/requests">Requests</Link>
              </li>
              <li>
                <Link to="/admin/bills">Bills</Link>
              </li>
              <li>
                <Link to="/admin/customers">Customers</Link>
              </li>
              <li>
                <Link to="/admin/create-bill" className="btn-create">
                  + Create Bill
                </Link>
              </li>
            </>
          )}
        </ul>

        <div className="navbar-user">
          <span className="user-info">👤 {userName}</span>
          <button onClick={handleLogout} className="btn-logout">
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
