CREATE OR REPLACE FUNCTION tenant_history_trigger() RETURNS TRIGGER AS
'
BEGIN
    IF TG_OP = ''UPDATE'' THEN
        INSERT INTO house_history(date, type, house_id, person_id)
        VALUES (CURRENT_DATE, ''TENANT'', NEW.house_id, NEW.id);
    ELSE
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE TRIGGER tenant_history_update
    AFTER INSERT OR UPDATE
    ON person
    FOR EACH ROW
EXECUTE PROCEDURE tenant_history_trigger();

CREATE OR REPLACE FUNCTION owner_history_trigger() RETURNS TRIGGER AS
'
BEGIN
    IF TG_OP = ''UPDATE'' OR TG_OP = ''INSERT'' THEN
        IF NEW.person_id IS DISTINCT FROM OLD.person_id THEN
            INSERT INTO house_history(date, type, house_id, person_id)
            VALUES (CURRENT_DATE, ''OWNER'', new.house_id, new.person_id);
        END IF;
    END IF;
    RETURN new;
END;
' LANGUAGE plpgsql;

CREATE TRIGGER owner_history_update
    AFTER INSERT OR UPDATE
    ON person_house
    FOR EACH ROW
EXECUTE PROCEDURE owner_history_trigger();