DELETE FROM payments;
ALTER SEQUENCE payments_id_seq RESTART;
INSERT INTO payments (uuid, qr_code, status, external_id)
VALUES ('9ad62775-aea0-48a4-9e0e-42c7438fd844', '00020101021243650016COM.MERCADOLIBRE020130636dc0c2d38-6be5-4959-8a46-5801afd151115204000053039865802BR5909Test Test6009SAO PAULO62070503***6304FCE2', 'PENDENTE', null);