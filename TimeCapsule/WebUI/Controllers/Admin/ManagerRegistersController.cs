using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;

namespace TimeCapsule.WebUI.Controllers.Admin
{
    public class ManagerRegistersController : AdminBaseController
    {
        [HttpGet]
        public async Task<ActionResult<string>> Test1()
        {
            return "123";
        }
    }
}
