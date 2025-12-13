import api from './api';

export const authService = {
  // Register new customer
  register: async (userData) => {
    try {
      const response = await api.post('/auth/register', userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Login
  login: async (credentials) => {
    try {
      const response = await api.post('/auth/login', credentials);
      if (response.data.data.token) {
        localStorage.setItem('token', response.data.data.token);
        localStorage.setItem('role', response.data.data.role);
        localStorage.setItem('userId', response.data.data.userId);
        localStorage.setItem('userName', response.data.data.name);
        localStorage.setItem('email', response.data.data.email);
      }
      return response.data;
    } catch (error) {
      throw error.response?.data || error.message;
    }
  },

  // Logout
  logout: async () => {
    try {
      await api.post('/auth/logout');
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      localStorage.removeItem('userId');
      localStorage.removeItem('userName');
      localStorage.removeItem('email');
    }
  },

  // Validate token
  validateToken: async () => {
    try {
      const response = await api.get('/auth/validate');
      return response.data.success;
    } catch (error) {
      return false;
    }
  },

  // Get stored user info
  getUser: () => {
    return {
      token: localStorage.getItem('token'),
      role: localStorage.getItem('role'),
      userId: localStorage.getItem('userId'),
      name: localStorage.getItem('userName'),
      email: localStorage.getItem('email'),
    };
  },

  // Check if user is authenticated
  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },

  // Get user role
  getUserRole: () => {
    return localStorage.getItem('role');
  },
};
