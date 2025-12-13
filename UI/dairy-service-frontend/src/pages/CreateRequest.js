import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CreateRequest.css';
import { customerService } from '../services/customerService';

function CreateRequest() {
  const [formData, setFormData] = useState({
    requestDate: new Date().toISOString().split('T')[0],
    quantityLiters: '',
    note: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (!formData.quantityLiters) {
      setError('Please enter quantity');
      return;
    }

    setLoading(true);
    try {
      const response = await customerService.createRequest(formData);

      if (response.success) {
        setSuccess('Request created successfully!');
        setTimeout(() => {
          navigate('/customer/requests');
        }, 1500);
      } else {
        setError(response.message || 'Failed to create request');
      }
    } catch (err) {
      setError(err.message || 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-container">
      <h1 className="page-title">📝 Create New Request</h1>

      <div className="card">
        {error && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <form onSubmit={handleSubmit} className="create-request-form">
          <div className="form-group">
            <label htmlFor="requestDate">Request Date *</label>
            <input
              type="date"
              id="requestDate"
              name="requestDate"
              value={formData.requestDate}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="quantityLiters">Quantity (Liters) *</label>
            <input
              type="number"
              id="quantityLiters"
              name="quantityLiters"
              step="0.5"
              min="0"
              value={formData.quantityLiters}
              onChange={handleChange}
              required
              placeholder="Enter quantity in liters"
            />
          </div>

          <div className="form-group">
            <label htmlFor="note">Note (Optional)</label>
            <textarea
              id="note"
              name="note"
              value={formData.note}
              onChange={handleChange}
              placeholder="Add any special requests or notes"
              rows="4"
            />
          </div>

          <div className="form-buttons">
            <button type="submit" className="btn-primary" disabled={loading}>
              {loading ? 'Creating...' : 'Create Request'}
            </button>
            <button
              type="button"
              className="btn-secondary"
              onClick={() => navigate('/customer/requests')}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CreateRequest;
