CREATE TABLE billing_audit_outbox (
     id                 UUID DEFAULT gen_random_uuid() PRIMARY KEY,
     "user"             VARCHAR(255) NOT NULL,
     created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     operation_type     VARCHAR(255),
     volume_used        INT4,
     parcels_count      INT4,
     trucks_count       INT4,
     status             VARCHAR(50)
);
