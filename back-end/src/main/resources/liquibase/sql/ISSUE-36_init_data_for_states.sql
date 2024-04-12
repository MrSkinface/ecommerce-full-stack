--liquibase formatted sql

--changeset Mike:ISSUE-36

INSERT INTO state(name, country_id) VALUES
                                        ('Acre', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Alagoas', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Amapá', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Amazonas', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Bahia', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Ceará', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Distrito Federal', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Espírito Santo', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Goiás', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Maranhão', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Mato Grosso do Sul', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Mato Grosso', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Minas Gerais', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Paraná', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Paraíba', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Pará', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Pernambuco', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Piaui', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Rio de Janeiro', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Rio Grande do Norte', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Rio Grande do Sul', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Rondônia', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Roraima', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Santa Catarina', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Sergipe', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('São Paulo', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Tocantins', (SELECT id FROM country WHERE name = 'Brazil' LIMIT 1)),
                                        ('Alberta', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('British Columbia', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Manitoba', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('New Brunswick', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Newfoundland and Labrador', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Northwest Territories', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Nova Scotia', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Nunavut', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Ontario', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Prince Edward Island', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Quebec', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Saskatchewan', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Yukon', (SELECT id FROM country WHERE name = 'Canada' LIMIT 1)),
                                        ('Baden-Württemberg', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Bavaria', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Berlin', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Brandenburg', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Bremen', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Hamburg', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Hesse', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Lower Saxony', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Mecklenburg-Vorpommern', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('North Rhine-Westphalia', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Rhineland-Palatinate', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Saarland', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Saxony', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Saxony-Anhalt', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Schleswig-Holstein', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Thuringia', (SELECT id FROM country WHERE name = 'Germany' LIMIT 1)),
                                        ('Andhra Pradesh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Arunachal Pradesh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Assam', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Bihar', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Chhattisgarh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Goa', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Gujarat', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Haryana', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Himachal Pradesh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Jammu & Kashmir', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Jharkhand', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Karnataka', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Kerala', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Madhya Pradesh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Maharashtra', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Manipur', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Meghalaya', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Mizoram', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Nagaland', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Odisha', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Punjab', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Rajasthan', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Sikkim', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Tamil Nadu', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Telangana', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Tripura', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Uttar Pradesh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Uttarakhand', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('West Bengal', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Andaman and Nicobar Islands', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Chandigarh', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Dadra and Nagar Haveli', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Daman & Diu', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Lakshadweep', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Puducherry', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('The Government of NCT of Delhi', (SELECT id FROM country WHERE name = 'India' LIMIT 1)),
                                        ('Adıyaman', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Afyonkarahisar', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Ağrı', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Aksaray', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Amasya', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Ankara', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Antalya', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Ardahan', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Artvin', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Aydın', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Balıkesir', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bartın', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Batman', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bayburt', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bilecik', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bingöl', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bitlis', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bolu', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Burdur', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Bursa', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Çanakkale', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Çankırı', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Çorum', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Denizli', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Diyarbakır', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Düzce', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Edirne', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Elazığ', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Erzincan', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Erzurum', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Eskişehir', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Gaziantep', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Giresun', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Gümüşhane', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Hakkâri', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Hatay', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Iğdır', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Isparta', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('İstanbul', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('İzmir', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kahramanmaraş', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Karabük', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Karaman', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kars', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kastamonu', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kayseri', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kırıkkale', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kırklareli', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kırşehir', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kilis', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kocaeli', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Konya', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Kütahya', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Malatya', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Manisa', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Mardin', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Mersin', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Muğla', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Muş', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Nevşehir', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Niğde', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Ordu', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Osmaniye', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Rize', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Sakarya', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Samsun', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Siirt', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Sinop', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Sivas', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Şanlıurfa', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Şırnak', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Tekirdağ', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Tokat', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Trabzon', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Tunceli', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Uşak', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Van', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Yalova', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Yozgat', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Zonguldak', (SELECT id FROM country WHERE name = 'Turkey' LIMIT 1)),
                                        ('Alabama', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Alaska', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Arizona', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Arkansas', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('California', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Colorado', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Connecticut', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Delaware', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('District Of Columbia', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Florida', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Georgia', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Hawaii', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Idaho', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Illinois', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Indiana', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Iowa', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Kansas', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Kentucky', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Louisiana', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Maine', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Maryland', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Massachusetts', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Michigan', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Minnesota', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Mississippi', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Missouri', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Montana', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Nebraska', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Nevada', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('New Hampshire', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('New Jersey', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('New Mexico', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('New York', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('North Carolina', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('North Dakota', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Ohio', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Oklahoma', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Oregon', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Pennsylvania', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Rhode Island', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('South Carolina', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('South Dakota', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Tennessee', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Texas', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Utah', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Vermont', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Virginia', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Washington', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('West Virginia', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Wisconsin', (SELECT id FROM country WHERE name = 'United States' LIMIT 1)),
                                        ('Wyoming', (SELECT id FROM country WHERE name = 'United States' LIMIT 1));