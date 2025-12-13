export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const USER_ROLES = {
  CUSTOMER: 'CUSTOMER',
  ADMIN: 'ADMIN',
};

export const REQUEST_STATUS = {
  PENDING: 'PENDING',
  ACCEPTED: 'ACCEPTED',
  REJECTED: 'REJECTED',
  COMPLETED: 'COMPLETED',
};

export const BILL_STATUS = {
  PAID: 'PAID',
  UNPAID: 'UNPAID',
};

export const STATUS_COLORS = {
  PENDING: '#f39c12',
  ACCEPTED: '#27ae60',
  REJECTED: '#e74c3c',
  COMPLETED: '#3498db',
  PAID: '#27ae60',
  UNPAID: '#e74c3c',
};

export const STATUS_LABELS = {
  PENDING: 'Pending',
  ACCEPTED: 'Accepted',
  REJECTED: 'Rejected',
  COMPLETED: 'Completed',
  PAID: 'Paid',
  UNPAID: 'Unpaid',
};