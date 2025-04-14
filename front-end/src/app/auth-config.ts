import {SocialAuthServiceConfig} from '@abacritt/angularx-social-login';
import {GoogleLoginProvider} from '@abacritt/angularx-social-login';

export const authConfig: SocialAuthServiceConfig = {
    autoLogin: false,
    providers: [
        {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
                '830718366723-kmrp388ksu3ones8feue3bq6h4nvbn0b.apps.googleusercontent.com'
            ),
        },
    ],
};
