
local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refillTokens = tonumber(ARGV[2])
local refillDuration = tonumber(ARGV[3])
local currentTime = tonumber(ARGV[4])


local tokens = redis.call("HGET", key, "tokens")
local lastRefill = redis.call("HGET", key, "last_refill")

if not tokens then
    tokens = capacity
else
    tokens = tonumber(tokens)
end

if not lastRefill then
    lastRefill = currentTime
else
    lastRefill = tonumber(lastRefill)
end


local elapsed = currentTime - lastRefill

local refill = math.floor(
        elapsed * refillTokens / refillDuration
)

if refill > 0 then

    tokens = math.min(
            capacity,
            tokens + refill
    )

    lastRefill = currentTime
end


local allowed = 0
local retryAfter = 0

if tokens > 0 then

    tokens = tokens - 1

    allowed = 1

else

    retryAfter = refillDuration

end


redis.call(
        "HSET",
        key,
        "tokens",
        tokens,
        "last_refill",
        lastRefill
)

redis.call(
        "EXPIRE",
        key,
        refillDuration * 2
)



return {
        allowed,
        tokens,
        retryAfter
}