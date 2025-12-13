import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './MyRequests.css';
import { customerService } from '../services/customerService';
import LoadingSpinner from '../components/LoadingSpinner';
import { STATUS_COLORS, STATUS_LABELS } from '../utils/constants';

function MyRequests() {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadRequests();
  }, []);

  const loadRequests = async () => {
    try {
      setLoading(true);
      const data = await customerService.getRequests();
      setRequests(data);
    } catch (err) {
      setError(err.message || 'Failed to load requests');
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="page-container">
      <div className="page-header">
        <h1 className="page-title">📋 My Requests</h1>
        <Link to="/customer/create-request" className="btn-primary">
          + New Request
        </Link>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {requests.length === 0 ? (
        <div className="card">
          <div className="no-data">
            <p>No requests yet. Create your first request!</p>
          </div>
        </div>
      ) : (
        <div className="card">
          <div className="table-responsive">
            <table>
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Quantity (L)</th>
                  <th>Status</th>
                  <th>Note</th>
                  <th>Created</th>
                </tr>
              </thead>
              <tbody>
                {requests.map((req) => (
                  <tr key={req.id}>
                    <td>{new Date(req.requestDate).toLocaleDateString()}</td>
                    <td>{req.quantityLiters}</td>
                    <td>
                      <span
                        className="badge"
                        style={{
                          backgroundColor: STATUS_COLORS[req.status] + '20',
                          color: STATUS_COLORS[req.status],
                          border: `1px solid ${STATUS_COLORS[req.status]}`,
                        }}
                      >
                        {STATUS_LABELS[req.status]}
                      </span>
                    </td>
                    <td>{req.note || '-'}</td>
                    <td>{new Date(req.createdAt).toLocaleDateString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}

export default MyRequests;
