CREATE TABLE "payments" (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    qr_code VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    external_id VARCHAR
);
