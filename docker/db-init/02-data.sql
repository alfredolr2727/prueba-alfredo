-- ============================================
-- Datos de ejemplo
-- ============================================

-- Pool 1: München (24h, Público, On-Street)
INSERT INTO pool (dcs_pool_id, incoming_pool_id, open_24h, access, pool_location_type)
VALUES ('POOL:001232cf-8608-3939-8e70-7e3beec16eec', 'DEESWS0008', true, 'PUBLIC', 'ONSTREET');

INSERT INTO pool_location (pool_id, city, country_code, house_number, state, street, type, zip_code, latitude, longitude)
VALUES (1, 'München', 'DE', '3a', 'Bayern', 'Hauptstraße', 'ENTRY', '81678', 49.138744, 11.87634);

INSERT INTO charging_station (pool_id, dcs_cs_id, incoming_cs_id)
VALUES (1, 'CHARGING_STATION:5e296c55-fe2e-313a-bf19-08f5bf35d6b6', 'SL01577');

INSERT INTO charging_station_auth_method (charging_station_id, auth_method)
VALUES (1, 'REMOTE');

INSERT INTO charge_point (charging_station_id, dcs_cp_id, incoming_cp_id, dynamic_info_available, iso_normed_id)
VALUES (1, 'CHARGE_POINT:200a4e22-b996-3cd0-8505-7cf6f5690714', '+49*809*010*150850', true, true);

INSERT INTO connector (charge_point_id, plug_type, power_level)
VALUES (1, 'CHADEMO', 22);

-- Pool 2: Berlin (No 24h, Privado, On-Street)
INSERT INTO pool (dcs_pool_id, incoming_pool_id, open_24h, access, pool_location_type)
VALUES ('POOL:2e66b934-c589-384f-abcc-97f6120e4afa', 'DEBER0042', false, 'PRIVATE', 'ONSTREET');

INSERT INTO pool_location (pool_id, city, country_code, house_number, state, street, type, zip_code, latitude, longitude)
VALUES (2, 'Berlin', 'DE', '15', 'Berlin', 'Unter den Linden', 'ENTRY', '10117', 52.516275, 13.377704);

INSERT INTO charging_station (pool_id, dcs_cs_id, incoming_cs_id)
VALUES (2, 'CHARGING_STATION:7a8b9c0d-1e2f-3g4h-5i6j-7k8l9m0n1o2p', 'BL02345');

INSERT INTO charging_station_auth_method (charging_station_id, auth_method)
VALUES (2, 'REMOTE'), (2, 'UNKNOWN');

INSERT INTO charge_point (charging_station_id, dcs_cp_id, incoming_cp_id, dynamic_info_available, iso_normed_id)
VALUES (2, 'CHARGE_POINT:948ebcb1-59da-32ed-a2c4-f45b4a515a35', '+49*030*020*250960', true, true);

INSERT INTO connector (charge_point_id, plug_type, power_level)
VALUES (2, 'CHADEMO', 50);

-- Pool 3: Hamburg (24h, Público, Unknown)
INSERT INTO pool (dcs_pool_id, incoming_pool_id, open_24h, access, pool_location_type)
VALUES ('POOL:0c6fd997-c271-3ac8-abd9-ebab55212eda', 'DEHH0099', true, 'PUBLIC', 'UNKNOWN');

INSERT INTO pool_location (pool_id, city, country_code, house_number, state, street, type, zip_code, latitude, longitude)
VALUES (3, 'Hamburg', 'DE', '88', 'Hamburg', 'Reeperbahn', 'ENTRY', '20359', 53.549489, 9.957560);

INSERT INTO pool_location (pool_id, city, country_code, house_number, state, street, type, zip_code, latitude, longitude)
VALUES (3, 'Hamburg', 'DE', '90', 'Hamburg', 'Reeperbahn', 'UNKNOWN', '20359', 53.549620, 9.957720);

INSERT INTO charging_station (pool_id, dcs_cs_id, incoming_cs_id)
VALUES (3, 'CHARGING_STATION:3b4c5d6e-7f8g-9h0i-1j2k-3l4m5n6o7p8q', 'HH03456'),
       (3, 'CHARGING_STATION:9z8y7x6w-5v4u-3t2s-1r0q-9p8o7n6m5l4k', 'HH03457');

INSERT INTO charging_station_auth_method (charging_station_id, auth_method)
VALUES (3, 'REMOTE'), (4, 'UNKNOWN');

INSERT INTO charge_point (charging_station_id, dcs_cp_id, incoming_cp_id, dynamic_info_available, iso_normed_id)
VALUES (3, 'CHARGE_POINT:3ad0a06c-8dae-3451-8acd-267460a9afee', '+49*040*030*350970', true, false),
       (4, 'CHARGE_POINT:5bc6d7e8-9fa0-1b2c-3d4e-5f6a7b8c9d0e', '+49*040*030*350971', false, true);

INSERT INTO connector (charge_point_id, plug_type, power_level)
VALUES (3, 'CHADEMO', 150),
       (3, 'UNKNOWN', 22),
       (4, 'CHADEMO', 350);

-- Pool 4: Frankfurt (No 24h, Público, On-Street)
INSERT INTO pool (dcs_pool_id, incoming_pool_id, open_24h, access, pool_location_type)
VALUES ('POOL:4d5e6f7a-8b9c-0d1e-2f3a-4b5c6d7e8f9a', 'DEFF0123', false, 'PUBLIC', 'ONSTREET');

INSERT INTO pool_location (pool_id, city, country_code, house_number, state, street, type, zip_code, latitude, longitude)
VALUES (4, 'Frankfurt am Main', 'DE', '1', 'Hessen', 'Römerberg', 'ENTRY', '60311', 50.110924, 8.682127);

INSERT INTO charging_station (pool_id, dcs_cs_id, incoming_cs_id)
VALUES (4, 'CHARGING_STATION:6e7f8a9b-0c1d-2e3f-4a5b-6c7d8e9f0a1b', 'FF04567');

INSERT INTO charging_station_auth_method (charging_station_id, auth_method)
VALUES (5, 'REMOTE');

INSERT INTO charge_point (charging_station_id, dcs_cp_id, incoming_cp_id, dynamic_info_available, iso_normed_id)
VALUES (5, 'CHARGE_POINT:7f8a9b0c-1d2e-3f4a-5b6c-7d8e9f0a1b2c', '+49*069*040*450980', true, true),
       (5, 'CHARGE_POINT:8a9b0c1d-2e3f-4a5b-6c7d-8e9f0a1b2c3d', '+49*069*040*450981', true, true);

INSERT INTO connector (charge_point_id, plug_type, power_level)
VALUES (5, 'CHADEMO', 100),
       (6, 'CHADEMO', 100),
       (6, 'UNKNOWN', 50);

-- Pool 5: Köln (24h, Privado, On-Street)
INSERT INTO pool (dcs_pool_id, incoming_pool_id, open_24h, access, pool_location_type)
VALUES ('POOL:5e6f7a8b-9c0d-1e2f-3a4b-5c6d7e8f9a0b', 'DEKO0234', true, 'PRIVATE', 'ONSTREET');

INSERT INTO pool_location (pool_id, city, country_code, house_number, state, street, type, zip_code, latitude, longitude)
VALUES (5, 'Köln', 'DE', '50', 'Nordrhein-Westfalen', 'Hohe Straße', 'ENTRY', '50667', 50.937531, 6.958307);

INSERT INTO charging_station (pool_id, dcs_cs_id, incoming_cs_id)
VALUES (5, 'CHARGING_STATION:9b0c1d2e-3f4a-5b6c-7d8e-9f0a1b2c3d4e', 'KO05678');

INSERT INTO charging_station_auth_method (charging_station_id, auth_method)
VALUES (6, 'UNKNOWN');

INSERT INTO charge_point (charging_station_id, dcs_cp_id, incoming_cp_id, dynamic_info_available, iso_normed_id)
VALUES (6, 'CHARGE_POINT:0c1d2e3f-4a5b-6c7d-8e9f-0a1b2c3d4e5f', '+49*221*050*550990', false, false);

INSERT INTO connector (charge_point_id, plug_type, power_level)
VALUES (7, 'UNKNOWN', 22);