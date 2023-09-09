import * as React from 'react';
import Box from '@mui/material/Box';
import {
  GridRowModesModel,
  GridRowModes,
  DataGrid,
  GridColDef,
  GridRowId,
} from '@mui/x-data-grid';
import { useEffect } from "react";
import axios from "axios";
import { IconButton } from '@mui/material';
import LaunchIcon from '@mui/icons-material/Launch';

export default function ContractOverview () {
  const [ rows, setRows ] = React.useState([]);
  const [ rowModesModel, setRowModesModel ] = React.useState<GridRowModesModel>({});

  const handleDetailsClick = (id: GridRowId) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
  };

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_ADDRESS}/leasing-contracts/overview`)
      .then(response => {
        setRows(response.data.content);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);


  const columns: GridColDef[] = [
    {
      field: 'contractNumber',
      headerName: 'Contract Number',
      width: 280
    },
    {
      field: 'customerSummary',
      headerName: 'Customer',
      width: 180,
    },
    {
      field: 'vehicle',
      headerName: 'Vehicle',
      width: 180,
      renderCell: (params) => {
        const { vehicleBrand, vehicleModel, vehicleModelYear } = params.row;
        return `${vehicleBrand} ${vehicleModel} (${vehicleModelYear})`;
      },
    },
    {
      field: 'vehicleVin',
      headerName: 'VIN',
      width: 180,
      renderCell: (params) => {
        const { vehicleVin } = params.row;
        return vehicleVin === '' ? '-' : vehicleVin;
      },
    },
    {
      field: 'monthlyRate',
      headerName: 'Monthly Rate',
      width: 180,
      valueFormatter: (params) => `${params.value.toFixed(2)} €`
    },
    {
      field: 'vehiclePrice',
      headerName: 'Vehicle Price',
      width: 180,
      valueFormatter: (params) => params.value.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })
    },
    {
      field: 'actions',
      type: 'actions',
      headerName: 'Details',
      width: 100,
      cellClassName: 'actions',
      renderCell: (params) => (
        <IconButton onClick={() => handleDetailsClick(params.row.contractDetailsLink)}><LaunchIcon /></IconButton>
      )
    },
  ];

  return (
    <Box sx={{
      width: '100%',
      height: '500px',
      '@media (min-width:600px)': {
        width: '100%',
        height: '1000px',
      },
    }}>
      <DataGrid
        rows={ rows }
        getRowId={(row) => row.contractNumber}
        columns={ columns }
      />
    </Box>
  );
}
