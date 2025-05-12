import { environment } from '../environments/environment';


export abstract class BaseService {

  private apiUrlBase = environment.apiUrl+'/api/';

  protected getFullUrl(finalEndpointName: string): string {
    return this.apiUrlBase + finalEndpointName;
  }
}

