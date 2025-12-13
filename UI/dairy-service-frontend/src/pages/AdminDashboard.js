import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './AdminDashboard.css';
import { adminService } from '../services/adminService';
import LoadingSpinner from '../components/LoadingSpinner';

function AdminDashboard() {
  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      setLoading(true);
      const data = await adminService.getDashboard();
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
      <h1 className="page-title">👨‍💼 Admin Dashboard</h1>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="card-grid">
        <div className="stat-card">
          <h3>Today's Requests</h3>
          <div className="stat-value">{dashboard?.todayTotalRequests || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Pending Requests</h3>
          <div className="stat-value">{dashboard?.pendingRequestsCount || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Outstanding Amount</h3>
          <div className="stat-value">₹{dashboard?.totalOutstandingAmount || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Cash Collected</h3>
          <div className="stat-value">₹{dashboard?.totalCashCollected || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Total Customers</h3>
          <div className="stat-value">{dashboard?.totalCustomers || 0}</div>
        </div>
        <div className="stat-card">
          <h3>Today's Liters</h3>
          <div className="stat-value">{dashboard?.todayTotalLitersSold || 0}L</div>
        </div>
      </div>

      <div className="actions-grid">
        <Link to="/admin/requests" className="action-card">
          <div className="action-icon">📋</div>
          <h3>All Requests</h3>
          <p>Manage customer requests</p>
        </Link>
        <Link to="/admin/create-bill" className="action-card">
          <div className="action-icon">📝</div>
          <h3>Create Bill</h3>
          <p>Generate new bills</p>
        </Link>
        <Link to="/admin/bills" className="action-card">
          <div className="action-icon">💰</div>
          <h3>Manage Bills</h3>
          <p>View and update bills</p>
        </Link>
        <Link to="/admin/customers" className="action-card">
          <div className="action-icon">👥</div>
          <h3>Customers</h3>
          <p>View all customers</p>
        </Link>
      </div>
    </div>
  );
}

export default AdminDashboard;
