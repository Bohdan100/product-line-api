-- "user" table
INSERT INTO "user" (username, password, name, surname) VALUES
('admin', '{noop}admin_secret', 'Admin', 'Main'),
('alice', '{noop}alice_secret', 'Alice', 'Johnson'),
('bob', '{noop}bob_secret', 'Bob', 'Smith'),
('charlie', '{noop}charlie_secret', 'Charlie', 'Brown');

-- user_role table
INSERT INTO user_role (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'SUPERIOR'),
(3, 'OPERATOR'),
(4, 'OPERATOR');

-- line table
INSERT INTO line (line_name) VALUES
('Manufacturing Line A'),
('Quality Control B'),
('Supply Chain Process C'),
('Business Development D'),
('Data Analysis Pipeline E');

-- record table
INSERT INTO record (date, start_time, end_time, name_of_organization, name_of_product, variant, side, quantity, line_id, author_id) VALUES
-- Manufacturing Line A
('2026-01-01', '08:00:00', '16:00:00', 'NexLine Systems', 'Raw Materials', 'Standard', 'Large', 1200, 1, 2), -- Material Order #101
('2026-01-02', '09:00:00', '17:00:00', 'OptiFlow Solutions', 'Shift Schedule', 'Update', 'Small', 15, 1, 3), -- Shift Schedule Update
('2026-01-03', '10:00:00', '18:00:00', 'Apex Manufacturing', 'Machine Maintenance', 'Routine', 'Medium', 5, 1, 4), -- Machine Maintenance
('2026-01-04', '11:00:00', '19:00:00', 'NexLine Systems', 'Production Goals', 'Q1', 'Small', 100, 1, 3), -- Production Goals Q1
('2026-01-05', '08:00:00', '16:00:00', 'CoreTrack Industries', 'Output Report', 'Daily', 'Large', 1200, 1, 2), -- Output Report

-- Quality Control B
('2026-01-06', '08:00:00', '16:00:00', 'OptiFlow Solutions', 'Inspection Report', 'Inventory', 'Medium', 50, 2, 2), -- Inspection Report #501
('2026-01-07', '09:00:00', '17:00:00', 'Stellar Supply Co.', 'Training Session', 'Inspection', 'Large', 12, 2, 3), -- Training Session
('2026-01-08', '10:00:00', '18:00:00', 'NexLine Systems', 'QA Tool Calibration', 'Standard', 'Medium', 10, 2, 4), -- QA Tool Calibration
('2026-01-09', '11:00:00', '19:00:00', 'PrimeNode Corp', 'Issue Log Update', 'Surface scratches', 'Small', 8, 2, 2), -- Issue Log Update
('2026-01-10', '08:00:00', '16:00:00', 'CoreTrack Industries', 'Rejected Batch', 'Routine', 'Small', 0, 2, 3), -- Rejected Batch

-- Supply Chain Process C
('2026-01-11', '08:00:00', '16:00:00', 'Lumix Product Group', 'Delivery Schedule', 'Raw Material', 'Medium', 500, 3, 4), -- Delivery Schedule
('2026-01-12', '09:00:00', '17:00:00', 'NexLine Systems', 'Transport Issues', 'Delays', 'Large', 10, 3, 2), -- Transport Issues
('2026-01-13', '10:00:00', '18:00:00', 'Lumix Product Group', 'Supplier Contract', 'Product Improvement', 'Large', 0, 3, 3), -- Supplier Contract
('2026-01-14', '11:00:00', '19:00:00', 'Vantage Logistics', 'Stock Level Check', 'Inventory', 'Large', 30, 3, 4), -- Stock Level Check
('2026-01-15', '08:00:00', '16:00:00', 'EchoSync Dynamics', 'Warehouse Capacity', 'Optimization', 'Small', 1000, 3, 2), -- Warehouse Capacity

-- Business Development D
('2026-01-16', '08:00:00', '16:00:00', 'Stellar Supply Co.', 'Market Analysis', 'Competitor Pricing', 'Medium', 0, 4, 3), -- Market Analysis
('2026-01-17', '09:00:00', '17:00:00', 'OptiFlow Solutions', 'Partnership Deal', 'Distribution', 'Medium', 0, 4, 2), -- Partnership Deal
('2026-01-18', '10:00:00', '18:00:00', 'Stellar Supply Co.', 'Customer Feedback', 'Product Improvement', 'Small', 100, 4, 4), -- Customer Feedback
('2026-01-19', '11:00:00', '19:00:00', 'Zenith Resource Management', 'Sales Goals', 'Monthly', 'Small', 500, 4, 2), -- Sales Goals
('2026-01-20', '08:00:00', '16:00:00', 'NexLine Systems', 'Training Program', 'Sales Team', 'Large', 30, 4, 3), -- Training Program

-- Data Analysis Pipeline E
('2026-01-21', '08:00:00', '16:00:00', 'NexLine Systems', 'Data Clean-up', 'Redundant entries', 'Large', 0, 5, 4), -- Data Clean-up
('2026-01-22', '09:00:00', '17:00:00', 'EchoSync Dynamics', 'Visualization Update', 'Dashboard', 'Small', 150, 5, 2), -- Visualization Update
('2026-01-23', '10:00:00', '18:00:00', 'Vantage Logistics', 'Algorithm Improvement', 'Accuracy', 'Medium', 80, 5, 3), -- Algorithm Improvement
('2026-01-24', '11:00:00', '19:00:00', 'Lumix Product Group', 'Large File Processing', 'Sales Team', 'Medium', 5000, 5, 4), -- Large File Processing
('2026-01-25', '08:00:00', '16:00:00', 'PrimeNode Corp', 'Anomaly Detection', 'Unusual patterns', 'Medium', 5, 5, 2), -- Anomaly Detection

-- Additional examples
('2026-01-26', '08:00:00', '16:00:00', 'OptiFlow Solutions', 'Cross-Line Review', 'Performance review', 'Medium', 100, 1, 1), -- Cross-Line Review
('2026-01-27', '09:00:00', '17:00:00', 'Apex Manufacturing', 'Emergency Plan', 'Optimization', 'Large', 10, 2, 1), -- Emergency Plan
('2026-01-28', '10:00:00', '18:00:00', 'CoreTrack Industries', 'Supplier Audit', 'Delays', 'Small', 50, 3, 1), -- Supplier Audit
('2026-01-29', '11:00:00', '19:00:00', 'Zenith Resource Management', 'Innovation Session', 'Raw Material', 'Medium', 40, 4, 1), -- Innovation Session
('2026-01-30', '08:00:00', '16:00:00', 'Vantage Logistics', 'Reporting Standards', 'Unified templates', 'Large', 10, 5, 1); -- Reporting Standards
