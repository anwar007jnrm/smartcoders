import React, { useState} from 'react';
import { TextField, MenuItem, FormControl, FormLabel, RadioGroup, FormControlLabel, Radio } from '@mui/material';

interface FieldOption {
  value: string | number;
  label: string;
}

interface FieldProps {
  type: string;
  label?: string;
  name: string;
  options?: FieldOption[] | string[];
  value?: string | number;
  required?: boolean;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  [key: string]: any;
}

function Eelement({ field }: { field: FieldProps }) {
  const { type, label, name, options, value, required, onChange, ...rest } = field;


  switch (type) {
    case 'radio':
      return (
        <FormControl component="fieldset" required={required} margin="normal" fullWidth>
          <FormLabel component="legend">{label || name}</FormLabel>
          <RadioGroup
            name={name}
            value={value ?? ''}
            onChange={onChange}
            row
          >
            {options &&
              options.map((option, idx) => {
                const optValue = typeof option === 'string' ? option : option.value;
                const optLabel = typeof option === 'string' ? option : option.label;
                return (
                  <FormControlLabel
                    key={idx}
                    value={optValue}
                    control={<Radio />}
                    label={optLabel}
                  />
                );
              })}
          </RadioGroup>
        </FormControl>
      );
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
          value={value}
          required={required}
          onChange={onChange}
          {...rest}
        />
      );
    case 'select':
      return (
        <TextField
          select
          fullWidth
          margin="normal"
          size="small"
          label={label || name}
          name={name}
          value={value}
          required={required}
          onChange={onChange}
          {...rest}
        >
          {options &&
            options.map((option, idx) => {
              const optValue = typeof option === 'string' ? option : option.value;
              const optLabel = typeof option === 'string' ? option : option.label;
              return (
                <MenuItem key={idx} value={optValue}>
                  {optLabel}
                </MenuItem>
              );
            })}
        </TextField>
      );
    default:
      return (
        <TextField
          fullWidth
          margin="normal"
          label={label || name}
          name={name}
          value={value}
          required={required}
          onChange={onChange}
          {...rest}
        />
      );
  }
}

export default Eelement;