import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';

function ProtectedRoute({ requiredRole }) {
  const token = localStorage.getItem('token');
  const userRole = localStorage.getItem('role');

  // Not authenticated
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Authenticated but wrong role
  if (requiredRole && userRole !== requiredRole) {
    return <Navigate to="/login" replace />;
  }

  // Has access
  return <Outlet />;
}

export default ProtectedRoute;
