package fi.heina.tarkastuslista;

import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class EventsModel implements Parcelable {

    private int id, train_number;

    private Date departure_date, virroitin, train_phone, brakes, jkv, phone_app, suuntavalotpeilit, lahtolupa;

    public EventsModel(int id, int train_number, Date departure_date, Date virroitin, Date train_phone, Date brakes, Date jkv, Date phone_app, Date suuntavalotpeilit, Date lahtolupa) {
        this.id = id;
        this.train_number = train_number;
        this.departure_date = departure_date;
        this.virroitin = virroitin;
        this.train_phone = train_phone;
        this.brakes = brakes;
        this.jkv = jkv;
        this.phone_app = phone_app;
        this.suuntavalotpeilit = suuntavalotpeilit;
        this.lahtolupa = lahtolupa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrain_number() {
        return train_number;
    }

    public void setTrain_number(int train_number) {
        this.train_number = train_number;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public Date getVirroitin() {
        return virroitin;
    }

    public void setVirroitin(Date virroitin) {
        this.virroitin = virroitin;
    }

    public Date getTrain_phone() {
        return train_phone;
    }

    public void setTrain_phone(Date train_phone) {
        this.train_phone = train_phone;
    }

    public Date getBrakes() {
        return brakes;
    }

    public void setBrakes(Date brakes) {
        this.brakes = brakes;
    }

    public Date getJkv() {
        return jkv;
    }

    public void setJkv(Date jkv) {
        this.jkv = jkv;
    }

    public Date getPhone_app() {
        return phone_app;
    }

    public void setPhone_app(Date phone_app) {
        this.phone_app = phone_app;
    }

    public Date getSuuntavalotpeilit() {
        return suuntavalotpeilit;
    }

    public void setSuuntavalotpeilit(Date suuntavalotpeilit) {
        this.suuntavalotpeilit = suuntavalotpeilit;
    }

    public Date getLahtolupa() {
        return lahtolupa;
    }

    public void setLahtolupa(Date lahtolupa) {
        this.lahtolupa = lahtolupa;
    }


    protected EventsModel(Parcel in) {
        train_number = in.readInt();
        departure_date = (Date) in.readSerializable();
        virroitin = (Date) in.readSerializable();
        train_phone = (Date) in.readSerializable();
        brakes = (Date) in.readSerializable();
        jkv = (Date) in.readSerializable();
        phone_app = (Date) in.readSerializable();
        suuntavalotpeilit = (Date) in.readSerializable();
        lahtolupa = (Date) in.readSerializable();

    }

    public static final Creator<EventsModel> CREATOR = new Creator<EventsModel>() {
        @Override
        public EventsModel createFromParcel(Parcel in) {
            return new EventsModel(in);
        }

        @Override
        public EventsModel[] newArray(int size) {
            return new EventsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(train_number);
        dest.writeSerializable(departure_date);
        dest.writeSerializable(virroitin);
        dest.writeSerializable(train_phone);
        dest.writeSerializable(brakes);
        dest.writeSerializable(jkv);
        dest.writeSerializable(phone_app);
        dest.writeSerializable(suuntavalotpeilit);
        dest.writeSerializable(lahtolupa);
    }

}
