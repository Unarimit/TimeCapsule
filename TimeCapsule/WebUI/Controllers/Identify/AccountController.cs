using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using TimeCapsule.Application.Users.Queries.LoginUser;

namespace TimeCapsule.WebUI.Controllers.Identify
{
    public class AccountController : IdentifyBaseController
    {
        [HttpPost("login")]
        public async Task<ActionResult> loginUser([FromBody]LoginUserQuery query)
        {
            var result = await Mediator.Send(query);
            if (result == null)
            {
                return Forbid();
            }

            var claims = new List<Claim>() {
                new Claim(ClaimTypes.Name, result.Username)
            };
            if (result.IsAdmin)
            {
                claims.Add(new Claim("myadmin", "abc"));
            }
            if (result.IsUser)
            {
                claims.Add(new Claim("myuser", "abc"));
            }
            var principal = new ClaimsPrincipal(new ClaimsIdentity(claims, "Cookies", "user", "role"));
            await HttpContext.SignInAsync(principal);
            return Ok();
        }

        [HttpGet("logout")]
        public async Task<ActionResult> Logout()
        {
            await HttpContext.SignOutAsync();
            return Ok();
        }

        [HttpGet("forbid")]
        public string MyForbid()
        {
            Response.StatusCode = 403;
            return "not authorize";
        }
    }
}
