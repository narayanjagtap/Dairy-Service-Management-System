import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './CustomerDashboard.css';
import { customerService } from '../services/customerService';
import LoadingSpinner from '../components/LoadingSpinner';

function CustomerDashboard() {
  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      setLoading(true);
      const data = await customerService.getDashboard();
      setDashboard(data);
    } catch (err) {
      setError(err.message || 'Failed to load dashboard');
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="page-container">
      
      <h1 className="page-title">👋 Welcome, {dashboard?.name}!</h1>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="card-grid">
        <div className="stat-card">
          <h3>Total Requests</h3>
          <div className="stat-value">{dashboard?.totalRequests || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Pending Requests</h3>
          <div className="stat-value">{dashboard?.pendingRequests || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Outstanding Amount</h3>
          <div className="stat-value">₹{dashboard?.totalOutstandingAmount || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Today's Request</h3>
          <div className="stat-value">
            {dashboard?.todayRequestStatus === 'NO_REQUEST' ? '❌' : '✅'}
          </div>
          <p>{dashboard?.todayRequestStatus}</p>
        </div>
      </div>

      <div className="actions-grid">
        <Link to="/customer/create-request" className="action-card">
          <div className="action-icon">📝</div>
          <h3>Create Request</h3>
          <p>Place a new milk request</p>
        </Link>
        <Link to="/customer/requests" className="action-card">
          <div className="action-icon">📋</div>
          <h3>My Requests</h3>
          <p>View all your requests</p>
        </Link>
        <Link to="/customer/bills" className="action-card">
          <div className="action-icon">💰</div>
          <h3>My Bills</h3>
          <p>View and track bills</p>
        </Link>
      </div>
    </div>
  );
}

export default CustomerDashboard;
