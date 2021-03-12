using Microsoft.AspNetCore.Http;
using System.Security.Claims;
using TimeCapsule.Application.Common.Interfaces;

namespace TimeCapsule.WebUI.Services
{
    public class CurrentUserService : ICurrentUserService
    {
        public CurrentUserService(IHttpContextAccessor httpContextAccessor)
        {
            Username = httpContextAccessor.HttpContext?.User?.FindFirstValue(ClaimTypes.Name);
        }

        public string Username{ get; }
    }
}
