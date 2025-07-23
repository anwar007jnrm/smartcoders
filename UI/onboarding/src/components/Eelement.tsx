import React from 'react';
import { TextField, MenuItem } from '@mui/material';

interface FieldOption {
  value: string | number;
  label: string;
}

interface FieldProps {
  type: string;
  label?: string;
  name: string;
  options?: FieldOption[];
  [key: string]: any;
}

function Eelement({ field }: { field: FieldProps }) {
  const { type, label, name, options, ...rest } = field;

  switch (type) {
    case 'text':
    case 'email':
    case 'number':
    case 'date':
    case 'password':
      return (
        <TextField
          fullWidth
          margin="normal"
          type={type}
          label={label || name}
          name={name}
          {...rest}
        />
      );
    case 'select':
      return (
        <TextField
          select
          fullWidth
          margin="normal"
          label={label || name}
          name={name}
          {...rest}
        >
          {options && options.map((option, idx) => (
            <MenuItem key={idx} value={option.value}>
              {option.label}
            </MenuItem>
          ))}
        </TextField>
      );
    default:
      return (
        <TextField
          fullWidth
          margin="normal"
          label={label || name}
          name={name}
          {...rest}
        />
      );
  }
}

export default Eelement;