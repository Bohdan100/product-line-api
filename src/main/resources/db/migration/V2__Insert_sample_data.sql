-- Вставка пользователей в таблицу "user"
INSERT INTO "user" (username, password, name, surname) VALUES
('admin', 'admin_hashed_password', 'Admin', 'User'),
('alice', 'alice_hashed_password', 'Alice', 'Johnson'),
('bob', 'bob_hashed_password', 'Bob', 'Smith'),
('charlie', 'charlie_hashed_password', 'Charlie', 'Brown');

-- Вставка ролей пользователей в таблицу user_role
INSERT INTO user_role (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'SUPERIOR'),
(3, 'OPERATOR'),
(4, 'OPERATOR');

-- Вставка линий в таблицу line
INSERT INTO line (line_name) VALUES
('Manufacturing Line A'),
('Quality Control B'),
('Supply Chain Process C'),
('Business Development D'),
('Data Analysis Pipeline E');

-- Вставка записей в таблицу record
INSERT INTO record (date, start_time, end_time, name_of_organization, name_of_product, variant, side, quantity, line_id, author_id) VALUES
-- Manufacturing Line A
('2025-01-01', '08:00:00', '16:00:00', 'Company ABC', 'Raw Materials', 'Standard', 'A', 1200, 1, 2), -- Material Order #101
('2025-01-02', '09:00:00', '17:00:00', 'Company ABC', 'Shift Schedule', 'Update', 'B', 15, 1, 3), -- Shift Schedule Update
('2025-01-03', '10:00:00', '18:00:00', 'Company ABC', 'Machine Maintenance', 'Routine', 'C', 5, 1, 4), -- Machine Maintenance
('2025-01-04', '11:00:00', '19:00:00', 'Company ABC', 'Production Goals', 'Q1', 'D', 100, 1, 3), -- Production Goals Q1
('2025-01-05', '08:00:00', '16:00:00', 'Company ABC', 'Output Report', 'Daily', 'E', 1200, 1, 2), -- Output Report

-- Quality Control B
('2025-01-06', '08:00:00', '16:00:00', 'Company ABC', 'Inspection Report', '#501', 'A', 50, 2, 2), -- Inspection Report #501
('2025-01-07', '09:00:00', '17:00:00', 'Company ABC', 'Training Session', 'Inspection', 'B', 12, 2, 3), -- Training Session
('2025-01-08', '10:00:00', '18:00:00', 'Company ABC', 'QA Tool Calibration', 'Standard', 'C', 10, 2, 4), -- QA Tool Calibration
('2025-01-09', '11:00:00', '19:00:00', 'Company ABC', 'Issue Log Update', 'Surface scratches', 'D', 8, 2, 2), -- Issue Log Update
('2025-01-10', '08:00:00', '16:00:00', 'Company ABC', 'Rejected Batch', '#305', 'E', 0, 2, 3), -- Rejected Batch

-- Supply Chain Process C
('2025-01-11', '08:00:00', '16:00:00', 'Company ABC', 'Delivery Schedule', 'Raw Material', 'A', 500, 3, 4), -- Delivery Schedule
('2025-01-12', '09:00:00', '17:00:00', 'Company ABC', 'Transport Issues', 'Delays', 'B', 10, 3, 2), -- Transport Issues
('2025-01-13', '10:00:00', '18:00:00', 'Company ABC', 'Supplier Contract', '#203', 'C', 0, 3, 3), -- Supplier Contract
('2025-01-14', '11:00:00', '19:00:00', 'Company ABC', 'Stock Level Check', 'Inventory', 'D', 30, 3, 4), -- Stock Level Check
('2025-01-15', '08:00:00', '16:00:00', 'Company ABC', 'Warehouse Capacity', 'Optimization', 'E', 1000, 3, 2), -- Warehouse Capacity

-- Business Development D
('2025-01-16', '08:00:00', '16:00:00', 'Company ABC', 'Market Analysis', 'Competitor Pricing', 'A', 0, 4, 3), -- Market Analysis
('2025-01-17', '09:00:00', '17:00:00', 'Company ABC', 'Partnership Deal', 'Distribution', 'B', 0, 4, 2), -- Partnership Deal
('2025-01-18', '10:00:00', '18:00:00', 'Company ABC', 'Customer Feedback', 'Product Improvement', 'C', 100, 4, 4), -- Customer Feedback
('2025-01-19', '11:00:00', '19:00:00', 'Company ABC', 'Sales Goals', 'Monthly', 'D', 500, 4, 2), -- Sales Goals
('2025-01-20', '08:00:00', '16:00:00', 'Company ABC', 'Training Program', 'Sales Team', 'E', 30, 4, 3), -- Training Program

-- Data Analysis Pipeline E
('2025-01-21', '08:00:00', '16:00:00', 'Company ABC', 'Data Clean-up', 'Redundant entries', 'A', 0, 5, 4), -- Data Clean-up
('2025-01-22', '09:00:00', '17:00:00', 'Company ABC', 'Visualization Update', 'Dashboard', 'B', 150, 5, 2), -- Visualization Update
('2025-01-23', '10:00:00', '18:00:00', 'Company ABC', 'Algorithm Improvement', 'Accuracy', 'C', 80, 5, 3), -- Algorithm Improvement
('2025-01-24', '11:00:00', '19:00:00', 'Company ABC', 'Large File Processing', '1TB of raw data', 'D', 5000, 5, 4), -- Large File Processing
('2025-01-25', '08:00:00', '16:00:00', 'Company ABC', 'Anomaly Detection', 'Unusual patterns', 'E', 5, 5, 2), -- Anomaly Detection

-- Дополнительные перекрестные примеры
('2025-01-26', '08:00:00', '16:00:00', 'Company ABC', 'Cross-Line Review', 'Performance review', 'A', 100, 1, 1), -- Cross-Line Review
('2025-01-27', '09:00:00', '17:00:00', 'Company ABC', 'Emergency Plan', 'Safety review Line B & C', 'B', 10, 2, 1), -- Emergency Plan
('2025-01-28', '10:00:00', '18:00:00', 'Company ABC', 'Supplier Audit', 'Lines A & C', 'C', 50, 3, 1), -- Supplier Audit
('2025-01-29', '11:00:00', '19:00:00', 'Company ABC', 'Innovation Session', 'New processes for Lines D & E', 'D', 40, 4, 1), -- Innovation Session
('2025-01-30', '08:00:00', '16:00:00', 'Company ABC', 'Reporting Standards', 'Unified templates', 'E', 10, 5, 1); -- Reporting Standards
