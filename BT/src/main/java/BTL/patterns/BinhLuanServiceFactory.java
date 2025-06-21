package BTL.patterns;

import BTL.services.BinhLuanServiceProxy;
import BTL.services.IBinhLuanService;

public class BinhLuanServiceFactory {
   public BinhLuanServiceFactory() {
   }

   public static IBinhLuanService createService() {
      return new BinhLuanServiceProxy();
   }
}