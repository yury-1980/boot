CREATE OR REPLACE FUNCTION update_house_history()
    RETURNS TRIGGER AS $$
DECLARE
    person_type PersonType;
BEGIN
    IF NEW.ownersList <> OLD.ownersList THEN
        person_type := 'OWNER';
    ELSIF NEW.residentsList <> OLD.residentsList THEN
        person_type := 'TENANT';
    ELSE
        RETURN NULL;
    END IF;

    INSERT INTO house_history (date, type, house_id, person_id)
    VALUES (CURRENT_DATE, person_type, NEW.id, NULL); -- Здесь нужно указать person_id, если вы хотите записывать информацию о конкретном человеке

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER house_update_trigger
    AFTER UPDATE ON house
    FOR EACH ROW
EXECUTE FUNCTION update_house_history();