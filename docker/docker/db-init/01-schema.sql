-- ============================================
-- Schema SIMPLIFICADO para PoolSearchResponse
-- Base de datos: PostgreSQL
-- ============================================

-- Tabla principal: Pool
CREATE TABLE IF NOT EXISTS pool (
                                    id BIGSERIAL PRIMARY KEY,
                                    dcs_pool_id VARCHAR(255) NOT NULL UNIQUE,
    incoming_pool_id VARCHAR(255),
    open_24h BOOLEAN,
    access VARCHAR(50),
    pool_location_type VARCHAR(50)
    );

-- Tabla: Pool Location
CREATE TABLE IF NOT EXISTS pool_location (
                                             id BIGSERIAL PRIMARY KEY,
                                             pool_id BIGINT REFERENCES pool(id) ON DELETE CASCADE,
    city VARCHAR(255),
    country_code VARCHAR(2),
    house_number VARCHAR(50),
    state VARCHAR(255),
    street VARCHAR(255),
    type VARCHAR(50),
    zip_code VARCHAR(20),
    latitude DECIMAL(10, 7),
    longitude DECIMAL(10, 7)
    );

-- Tabla: Charging Station
CREATE TABLE IF NOT EXISTS charging_station (
                                                id BIGSERIAL PRIMARY KEY,
                                                pool_id BIGINT REFERENCES pool(id) ON DELETE CASCADE,
    dcs_cs_id VARCHAR(255) NOT NULL UNIQUE,
    incoming_cs_id VARCHAR(255)
    );

-- Tabla: Charging Station Auth Methods
CREATE TABLE IF NOT EXISTS charging_station_auth_method (
                                                            id BIGSERIAL PRIMARY KEY,
                                                            charging_station_id BIGINT REFERENCES charging_station(id) ON DELETE CASCADE,
    auth_method VARCHAR(50)
    );

-- Tabla: Charge Point
CREATE TABLE IF NOT EXISTS charge_point (
                                            id BIGSERIAL PRIMARY KEY,
                                            charging_station_id BIGINT REFERENCES charging_station(id) ON DELETE CASCADE,
    dcs_cp_id VARCHAR(255) NOT NULL UNIQUE,
    incoming_cp_id VARCHAR(255),
    dynamic_info_available BOOLEAN,
    iso_normed_id BOOLEAN
    );

-- Tabla: Connector
CREATE TABLE IF NOT EXISTS connector (
                                         id BIGSERIAL PRIMARY KEY,
                                         charge_point_id BIGINT REFERENCES charge_point(id) ON DELETE CASCADE,
    plug_type VARCHAR(50),
    power_level INTEGER
    );

-- ============================================
-- Índices MÍNIMOS (solo para IDs principales)
-- ============================================

CREATE INDEX idx_pool_dcs_pool_id ON pool(dcs_pool_id);
CREATE INDEX idx_pool_location_pool_id ON pool_location(pool_id);
CREATE INDEX idx_charging_station_pool_id ON charging_station(pool_id);
CREATE INDEX idx_charge_point_cs_id ON charge_point(charging_station_id);
CREATE INDEX idx_connector_cp_id ON connector(charge_point_id);