import React, {useState} from 'react';
import {Sidebar, Menu, MenuItem} from 'react-pro-sidebar';
import {SidebarHeader} from '../components/SidebarHeader';
import {Typography} from "@mui/material";
import ChecklistOutlinedIcon from '@mui/icons-material/ChecklistOutlined';
import {CarRentalOutlined, CreditScoreOutlined, PersonAddAltOutlined} from "@mui/icons-material";
import ContractOverview from "../components/ContractOverview";

function Dashboard() {
    const [selectedMenu, setSelectedMenu] = useState('Dashboard');

    const handleMenuClick = (menu) => {
        setSelectedMenu(menu);
    };

    return (
        <div style={{display: 'flex', height: '100%'}}>
            <Sidebar breakPoint="md">
                <SidebarHeader rtl={true} style={{marginBottom: '24px', marginTop: '16px'}}/>
                <div style={{flex: 1, marginBottom: '32px'}}>
                    <div style={{padding: '0 24px', marginBottom: '8px'}}>
                        <Typography
                            variant="body2"
                            fontWeight={600}>
                            Vehicle Leasing Modules
                        </Typography>
                    </div>
                    <Menu>
                        <MenuItem icon={<ChecklistOutlinedIcon/>}
                                  onClick={() => handleMenuClick('Dashboard')}>
                            Contract Overview
                        </MenuItem>
                        <MenuItem icon={<PersonAddAltOutlined/>}
                                  onClick={() => handleMenuClick('Customers')}>
                            Customers
                        </MenuItem>
                        <MenuItem icon={<CarRentalOutlined/>}
                                  onClick={() => handleMenuClick('Vehicles')}>
                            Vehicles
                        </MenuItem>
                        <MenuItem icon={<CreditScoreOutlined/>}
                                  onClick={() => handleMenuClick('Lease Contract')}>
                            Lease Contract
                        </MenuItem>
                    </Menu>
                </div>
            </Sidebar>

            <main style={{padding: '64px 64px', color: '#44596e'}}>
                {selectedMenu === 'Dashboard' && (
                    <div style={{marginBottom: '48px'}}>
                        <ContractOverview/>
                    </div>
                )}
                {selectedMenu === 'Customers' && (
                    <div>
                        <h1>Customers</h1>
                    </div>
                )}
                {selectedMenu === 'Vehicles' && (
                    <div>
                        <h1>Vehicles</h1>
                    </div>
                )}
                {selectedMenu === 'Lease Contract' && (
                    <div>
                        <h1>Lease Contract</h1>
                    </div>
                )}
            </main>
        </div>
    );
}

export default Dashboard
